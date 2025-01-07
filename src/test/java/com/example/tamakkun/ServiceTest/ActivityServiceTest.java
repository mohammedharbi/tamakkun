package com.example.tamakkun.ServiceTest;

import com.example.tamakkun.DTO_Out.ActivityDTO_Out;
import com.example.tamakkun.Model.Activity;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Repository.ActivityRepository;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import com.example.tamakkun.Service.ActivityService;
import com.example.tamakkun.Service.TextToSpeechService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceTest {

    @InjectMocks
    private ActivityService activityService;

    @Mock
    private ActivityRepository activityRepository;

    @Mock
    private CentreRepository centreRepository;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private TextToSpeechService textToSpeechService;


    private MyUser myUser;
    private Centre centre;
    private Activity activity;

    @BeforeEach
    void setUp() {
        myUser = new MyUser(1, "user", "password", "CENTRE", "user@example.com", null, null, null, null);
        centre = new Centre(1, "Centre Name", "description", "Riyadh", null, null, "123456", "789101", false, 100.0, "url", myUser, null, null, null, null, null, null);
        activity = new Activity(1, "Activity 1", "Description 1", null, centre);
    }

    @Test
    public void testGetAllActivitiesByCentre() {
        List<Activity> activities = new ArrayList<>();
        activities.add(activity);

        when(authRepository.findMyUserById(1)).thenReturn(myUser);
        when(centreRepository.findCentreById(1)).thenReturn(centre);
        when(activityRepository.findAll()).thenReturn(activities);

        List<ActivityDTO_Out> result = activityService.getAllActivitiesByCentre(1, 1);

        assertEquals(1, result.size());
        assertEquals("Activity 1", result.get(0).getName());

        verify(authRepository, times(1)).findMyUserById(1);
        verify(centreRepository, times(1)).findCentreById(1);
        verify(activityRepository, times(1)).findAll();
    }

    @Test
    public void addActivityTest() {
        when(authRepository.findMyUserById(1)).thenReturn(myUser);

        activityService.addActivity(1, activity);

        verify(authRepository, times(1)).findMyUserById(1);
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void updateActivityTest() {
        MyUser myUser = new MyUser();
        myUser.setId(1);
        Centre centre = new Centre();

        centre.setId(1);
        Activity activity = new Activity(1, "Old Activity", "Old Description", null, centre);

        when(centreRepository.findCentreById(1)).thenReturn(centre);
        when(activityRepository.findActivityById(1)).thenReturn(activity);

        Activity newActivity = new Activity(1, "Updated Activity", "Updated Description", null, centre);

        activityService.updateActivity(1, newActivity, 1);

        verify(centreRepository, times(1)).findCentreById(1);
        verify(activityRepository, times(1)).findActivityById(1);
        verify(activityRepository, times(1)).save(activity);
    }

    @Test
    public void deleteActivityTest() {
        MyUser myUser = new MyUser();
        myUser.setId(1);

        Centre centre = new Centre();
        centre.setId(1);

        Activity activity = new Activity(1, "Test Activity", "Test Description", null, centre);

        when(authRepository.findMyUserById(1)).thenReturn(myUser);
        when(activityRepository.findActivityById(1)).thenReturn(activity);

        activityService.deleteActivity(1, 1);

        verify(authRepository, times(1)).findMyUserById(1);
        verify(activityRepository, times(1)).findActivityById(1);
        verify(activityRepository, times(1)).delete(activity);
    }


    @Test
    public void getActivitiesByDisabilityTypeTest() {
        Integer userId = 1;
        String disabilityType = "Autism";

        MyUser myUser = new MyUser();
        myUser.setId(userId);
        Activity activity = new Activity(1, "Activity 1", "Description", Collections.singletonList("Autism"), null);
        List<Activity> activities = List.of(activity);

        when(authRepository.findMyUserById(userId)).thenReturn(myUser);
        when(activityRepository.findActivitiesByDisabilityType(disabilityType)).thenReturn(activities);

        List<ActivityDTO_Out> result = activityService.getActivitiesByDisabilityType(userId, disabilityType);

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("Activity 1", result.get(0).getName());
        assertEquals("Description", result.get(0).getDescription());

        verify(authRepository).findMyUserById(userId);
        verify(activityRepository).findActivitiesByDisabilityType(disabilityType);
    }



    @Test
    public void getActivityDescriptionAsAudioTest() {
        Integer activityId = 1;

        Activity activity = new Activity();
        activity.setId(activityId);
        activity.setDescription("This is an activity description.");

        when(activityRepository.findActivityById(activityId)).thenReturn(activity);


        when(textToSpeechService.convertTextToAudio(activity.getDescription())).thenReturn(new byte[]{1, 2, 3});

        byte[] result = activityService.getActivityDescriptionAsAudio(activityId);

        assertNotNull(result);
        assertArrayEquals(new byte[]{1, 2, 3}, result);

        verify(activityRepository).findActivityById(activityId);
        verify(textToSpeechService).convertTextToAudio(activity.getDescription());
    }


}
