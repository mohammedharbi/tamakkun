package com.example.tamakkun.Service;

import com.example.tamakkun.API.ApiException;
import com.example.tamakkun.DTO_Out.ActivityDTO_Out;
import com.example.tamakkun.DTO_Out.CentreDTO_Out;
import com.example.tamakkun.Model.Activity;
import com.example.tamakkun.Model.Centre;
import com.example.tamakkun.Model.MyUser;
import com.example.tamakkun.Repository.ActivityRepository;
import com.example.tamakkun.Repository.AuthRepository;
import com.example.tamakkun.Repository.CentreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final TextToSpeechService textToSpeechService;
    private final ActivityRepository activityRepository;
    private final CentreRepository centreRepository;
    private final AuthRepository authRepository;

    public List<ActivityDTO_Out> getAllActivities(){
        List<Activity> activities = activityRepository.findAll();
        List<ActivityDTO_Out>activityDTOOuts= new ArrayList<>();

        for(Activity activity : activities){
            ActivityDTO_Out activityDTOOut = new ActivityDTO_Out(activity.getName(), activity.getDescription(), activity.getAllowedDisabilities());
            activityDTOOuts.add(activityDTOOut);
        }
        return activityDTOOuts;
    }

    //Activity will be added if centre already there
    public void addActivity(Integer centre_id, Activity activity){
        MyUser user =authRepository.findMyUserById(centre_id);

        if(user==null)
            throw new ApiException("Centre not found!");

        activity.setCentre(user.getCentre());
        activityRepository.save(activity);
    }


    public void updateActivity(Integer activity_id, Activity newActivity, Integer centre_id) {
        Activity oldActivity = activityRepository.findActivityById(activity_id);
        if (oldActivity == null) {
            throw new ApiException("This activity not found!");
        }

        //check if the activity belongs to the specific centre
        if (!oldActivity.getCentre().getId().equals(centre_id)) {
            throw new ApiException("This centre doesn't have this activity to update it!");
        }

        // retrieve the associated Centre
        Centre centre = centreRepository.findCentreById(centre_id);
        if (centre == null) {
            throw new ApiException("Centre not found!");
        }

        oldActivity.setName(newActivity.getName());
        oldActivity.setDescription(newActivity.getDescription());
        oldActivity.setAllowedDisabilities(newActivity.getAllowedDisabilities());
        oldActivity.setCentre(centre);

        //save
        activityRepository.save(oldActivity);
    }

    public void deleteActivity(Integer activity_id, Integer centre_id){

        MyUser user=authRepository.findMyUserById(centre_id);
        if(user==null)
            throw new ApiException("Centre not found!");

        Activity activity = activityRepository.findActivityById(activity_id);

        if(activity==null)
            throw new ApiException("Activity not found!");
        activityRepository.delete(activity);
    }

    //End CRUD


    public List<ActivityDTO_Out> getActivitiesByDisabilityType(String disabilityType) {
        List<Activity> activities = activityRepository.findActivitiesByDisabilityType(disabilityType);


        return activities.stream().map(activity -> new ActivityDTO_Out(
                activity.getName(),
                activity.getDescription(),
                activity.getAllowedDisabilities()
        )).collect(Collectors.toList());
    }



    public byte[] getActivityDescriptionAsAudio(Integer activity_id) {
        // الحصول على المركز
        Activity activity = activityRepository.findActivityById(activity_id);
        if (activity == null) {
            throw new ApiException("This activity not found!");
        }

        // تحويل الوصف إلى صوت
        return textToSpeechService.convertTextToAudio(activity.getDescription());
    }












}
