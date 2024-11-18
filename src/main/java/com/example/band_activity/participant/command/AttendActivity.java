package com.example.band_activity.participant.command;

import com.example.band_activity.activity.Activity;
import com.example.band_activity.core.Command;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class AttendActivity extends Command {

    @NotNull
    private Long activityId;
    private Activity activity; //반드시 별도세팅
    @NotNull
    private Long memberId;
    private String memberName;
    private Boolean additional;
    private String profileName;

    public boolean isAdditional(){
        return additional!=null?additional:false;
    }
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
    public String getProfileName(){
        return profileName !=null? profileName :super.getUsername();
    }

    public AttendActivity(String username, Long activityId, Long clubId, Long memberId, String memberName, Boolean additional, String profileName) {
        super(username, clubId);
        this.activityId = activityId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.additional = additional;
        this.profileName = profileName;
    }
}
