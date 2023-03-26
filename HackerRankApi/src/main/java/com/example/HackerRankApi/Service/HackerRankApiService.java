package com.example.HackerRankApi.Service;

import com.example.HackerRankApi.Model.CommentsModel;
import com.example.HackerRankApi.Model.StoryModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class HackerRankApiService {
    private RestTemplate restTemplate;
    private static final String BASE_URL = "https://hacker-news.firebaseio.com/v0";

    public HackerRankApiService() {
        restTemplate = new RestTemplate();
    }

    public HackerRankApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // get the top 10 Stories
    public List<StoryModel> getTopStories() {
        int[] topStoryIds = restTemplate.getForObject(BASE_URL + "/topstories.json", int[].class);
        List<StoryModel> topStories = new ArrayList<>();
        for (int i = 0; i < 10 && i < topStoryIds.length; i++) {
            int storyId = topStoryIds[i];
            StoryModel story = restTemplate.getForObject(BASE_URL + "/item/{id}.json", StoryModel.class, storyId);
            if (story != null && Instant.now().getEpochSecond() - story.getTime() > TimeUnit.MINUTES.toSeconds(15)) {
                topStories.add(story);
            }
        }
        return topStories;
    }

    public StoryModel getStory(int id) {
        String url = BASE_URL + "/item/" + id + ".json";
        return restTemplate.getForObject(url, StoryModel.class);
    }

    // Return List of max 10 Comments sorted by kids
    public List<CommentsModel> getCommentIds(Integer id) {
        String url = BASE_URL + "/item/" + id + ".json";
        CommentsModel commentdata = restTemplate.getForObject(url, CommentsModel.class);
        if (commentdata == null) {
            return null;
        }
        List<CommentsModel> comments = new ArrayList<>();
        List<Integer> commentIds = commentdata.getKids();

        for (int i = 0; i < 10 && i < commentIds.size(); i++) {
            Integer commentId = commentIds.get(i);
            CommentsModel comment = getComment(commentId);
            comments.add(comment);
        }
        comments.sort(Comparator.comparingInt(CommentsModel::getChildCommentsCount).reversed());

        return comments;
    }

    private CommentsModel getComment(Integer commentId) {
        String url = BASE_URL + "/item/" + commentId + ".json";
        return restTemplate.getForObject(url, CommentsModel.class);
    }
}
