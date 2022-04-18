package com.thucnobita.api.instagram.actions;

import java.lang.reflect.Field;
import com.thucnobita.api.instagram.IGClient;
import com.thucnobita.api.instagram.actions.account.AccountAction;
import com.thucnobita.api.instagram.actions.igtv.IgtvAction;
import com.thucnobita.api.instagram.actions.search.SearchAction;
import com.thucnobita.api.instagram.actions.simulate.SimulateAction;
import com.thucnobita.api.instagram.actions.status.StatusAction;
import com.thucnobita.api.instagram.actions.story.StoryAction;
import com.thucnobita.api.instagram.actions.timeline.TimelineAction;
import com.thucnobita.api.instagram.actions.upload.UploadAction;
import com.thucnobita.api.instagram.actions.users.UsersAction;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

@Accessors(fluent = true, prefix = "_")
@Getter
public class IGClientActions {
    private UploadAction _upload;
    private TimelineAction _timeline;
    private StoryAction _story;
    private UsersAction _users;
    private SimulateAction _simulate;
    private IgtvAction _igtv;
    private AccountAction _account;
    private SearchAction _search;
    private StatusAction _status;

    @SneakyThrows
    public IGClientActions(IGClient client) {
        for (Field field : this.getClass().getDeclaredFields())
            if (field.getName().startsWith("_"))
                field.set(this, field.getType().getConstructor(IGClient.class).newInstance(client));
    }

}
