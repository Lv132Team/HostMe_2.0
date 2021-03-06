package com.softserve.edu.service.implementation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.softserve.edu.dto.PostDto;
import com.softserve.edu.model.Conversation;
import com.softserve.edu.model.Image;
import com.softserve.edu.model.Post;
import com.softserve.edu.model.User;
import com.softserve.edu.repositories.ConversationRepository;
import com.softserve.edu.repositories.PostRepository;
import com.softserve.edu.repositories.SystemPropertiesRepository;
import com.softserve.edu.service.PostService;
import com.softserve.edu.service.SystemPropertiesService;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private SystemPropertiesRepository systemPropertiesRepository;

    @Autowired
    private SystemPropertiesService systemPropertiesSerive;

    public final Integer PROPERTY_ID = 1;

    @Override
    @Transactional
    public List<PostDto> findByConversationId(Long id) {
	List<PostDto> result = new ArrayList<PostDto>();
	List<Post> posts = (List<Post>) postRepository.findByConversationId(id);
	Conversation conversation = conversationRepository.findOne(id);
	String propertiesImageUrl = systemPropertiesSerive.getImageUrl();
	String loggedUser = SecurityContextHolder.getContext()
		.getAuthentication().getName();

	boolean isModerator = false;
	Set<User> moderators = conversation.getModerators();
	if (moderators.size() > 0) {
	    for (User moderator : moderators) {
		if (moderator.getLogin().equals(loggedUser)) {
		    isModerator = true;
		}
	    }
	}
	if (conversation.getOwner().getLogin().equals(loggedUser)) {
	    isModerator = true;
	}

	for (Post post : posts) {
	    String image = null;
	    Iterator<Image> imageItr = post.getAuthor().getImages().iterator();
	    if (imageItr.hasNext()) {
		image = propertiesImageUrl + "/" + imageItr.next().getLink();
	    } else {
		image = "resources/images/defaultUserImage.png";
	    }
	    boolean isRemovable = false;
	    if (post.getAuthor().getLogin().equals(loggedUser)) {
		isRemovable = true;
	    } else if (isModerator) {
		isRemovable = true;
	    }
	    result.add(new PostDto(post, image, isRemovable));
	}
	return result;
    }

    @Override
    @Transactional
    public List<PostDto> findByPlaceId(Integer id) {
	List<PostDto> result = new ArrayList<PostDto>();
	List<Post> posts = (List<Post>) postRepository.findByPlaceId(id);
	String propertiesImageUrl = systemPropertiesSerive.getImageUrl();
	for (Post post : posts) {
	    String image = null;
	    Iterator<Image> imageItr = post.getAuthor().getImages().iterator();
	    if (imageItr.hasNext()) {
		image = propertiesImageUrl + "/" + imageItr.next().getLink();
	    }
	    result.add(new PostDto(post, image));
	}
	return result;
    }

    @Override
    public Post findOne(Long id) {
	return postRepository.findOne(id);

    }

    @Override
    @Transactional
    public Post save(Post post) {
	return postRepository.save(post);
    }

    @Override
    @Transactional
    @PreAuthorize("#post.author.login == authentication.name")
    public void delete(Post post) {
	postRepository.delete(post);
    }

    @Override
    public Long findConversationIdByPostId(Long id) {
	return postRepository.findConversationIdByPostId(id);
    }

}
