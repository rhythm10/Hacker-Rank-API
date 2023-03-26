package com.example.HackerRankApi.Controller;

import com.example.HackerRankApi.Model.CommentsModel;
import com.example.HackerRankApi.Model.StoryModel;
import com.example.HackerRankApi.Service.HackerRankApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class HackerRankApiController {
    @Autowired
    private HackerRankApiService hackerRankApiService;
    @Autowired
    private CacheManager cacheManager;

    public HackerRankApiController(HackerRankApiService hackerRankApiService, CacheManager cacheManager) {
        this.hackerRankApiService = hackerRankApiService;
        this.cacheManager = cacheManager;
    }

    @GetMapping("/top-stories")
    @Cacheable(value = "topStoriesCache", key = "'getTopStories'")
    public List<StoryModel> getTopStories() {
        Cache topStoriesCache = cacheManager.getCache("topStoriesCache");
        Cache.ValueWrapper cachedValue = topStoriesCache.get("getTopStories");
        if (cachedValue != null) {
            return (List<StoryModel>) cachedValue.get();
        }
        List<StoryModel> result = hackerRankApiService.getTopStories();
        Cache pastStoriesCache = cacheManager.getCache("pastStoriesCache");
        Cache.ValueWrapper pastCachedValue = pastStoriesCache.get("getPastStories");
        List<StoryModel> pastStories;
        if (pastCachedValue != null) {
            pastStories = (List<StoryModel>) pastCachedValue.get();
        } else {
            pastStories = new ArrayList<>();
        }
        pastStories.addAll(result);
        topStoriesCache.put("getTopStories", result);
        pastStoriesCache.put("getPastStories", pastStories);
        return result;
    }

    @Cacheable(value = "pastStoriesCache", key = "'getPastStories'")
    @GetMapping("/past-stories")
    public List<StoryModel> getPastStories() {
        Cache pastStoriesCache = cacheManager.getCache("pastStoriesCache");
        Cache.ValueWrapper cachedValue = pastStoriesCache.get("getPastStories");
        if (cachedValue != null) {
            return (List<StoryModel>) cachedValue.get();
        }
        return new ArrayList<>();
    }

    @Cacheable("comments")
    @GetMapping("/comments/{id}")
    public List<CommentsModel> getComments(@PathVariable Integer id) {
        List<CommentsModel> comments = null;
        if(hackerRankApiService.getStory(id) != null) {
            comments = hackerRankApiService.getCommentIds(id);
        }
        return comments;
    }

    @Scheduled(fixedRate = 900000)
    @CacheEvict(value = "topStoriesCache", allEntries = true)
    public void clearCacheSchedule(){
        for(String name:cacheManager.getCacheNames()){
            if(name.equals("topStoriesCache")) {
                cacheManager.getCache(name).clear();
            }
        }
    }

}
