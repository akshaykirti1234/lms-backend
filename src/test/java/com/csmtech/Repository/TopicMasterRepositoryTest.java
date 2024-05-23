package com.csmtech.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.csmtech.entity.ScheduleForMaster;
import com.csmtech.entity.TopicMaster;
import com.csmtech.entity.UserMaster;
import com.csmtech.repository.TopicMasterRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TopicMasterRepositoryTest {

    @Autowired
    private TopicMasterRepository topicMasterRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    
    public void testViewTopicData() {
        List<TopicMaster> topics = topicMasterRepository.viewTopicData();
        assertThat(topics).isNotNull().isNotEmpty();
        assertThat(topics.size()).isEqualTo(4);
    }

    @Test
    @Transactional
    public void testDeleteTopic() {
        // Create a TopicMaster entity
        TopicMaster topic = new TopicMaster();
        topic.setTopicId(1);
       
        topicMasterRepository.deleteTopic(1);

        // Check if topic is deleted
        TopicMaster deletedTopic = entityManager.find(TopicMaster.class, 1);
        assertThat(deletedTopic).isNotNull();
    }

    @Test
    public void testGetTopicById() {
        // Create a TopicMaster entity
        TopicMaster topic = new TopicMaster();
        topic.setTopicId(1);
        topic.setTopicName("topic");

        // Retrieve topic by ID
        TopicMaster retrievedTopic = topicMasterRepository.getTopicById(1);
        assertThat(retrievedTopic).isNotNull();
        assertThat(retrievedTopic.getTopicName()).isEqualTo("topic");
    }

    @Test
    public void testGetTopicByUserIdAndScheduleId() {
        // Create UserMaster entity
        UserMaster user = new UserMaster();
        user.setUserId(1);

        // Create ScheduleForMaster entity
        ScheduleForMaster scheduleFor = new ScheduleForMaster();
        scheduleFor.setScheduleForId(1);

        // Create TopicMaster entity
        TopicMaster topic = new TopicMaster();
        topic.setUserMaster(user);
        topic.setScheduleForMaster(scheduleFor);

        // Retrieve topic by user ID and schedule ID
        TopicMaster retrievedTopic = topicMasterRepository.getTopicByUserIdAndScheduleId(1, 1);
        assertThat(retrievedTopic).isNotNull();
        assertThat(retrievedTopic.getTopicId()).isEqualTo(5);
    }
}
