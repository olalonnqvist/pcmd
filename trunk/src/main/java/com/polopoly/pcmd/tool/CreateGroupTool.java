package com.polopoly.pcmd.tool;

import java.rmi.RemoteException;

import com.polopoly.pcmd.FatalToolException;
import com.polopoly.pcmd.field.content.AbstractPrincipalIdField;
import com.polopoly.user.server.Caller;
import com.polopoly.user.server.Group;
import com.polopoly.user.server.GroupId;
import com.polopoly.user.server.PrincipalId;
import com.polopoly.util.client.PolopolyContext;

public class CreateGroupTool implements Tool<CreateGroupParameters> {

    public CreateGroupParameters createParameters() {
        return new CreateGroupParameters();
    }

    public void execute(PolopolyContext context, CreateGroupParameters parameters) {
        Caller currentCaller = context.getPolicyCMServer().getCurrentCaller();

        for (String groupNameToCreate : parameters.getGroupNamesToCreate()) {
            Group group = null;

            GroupId[] groupIds = null;

            try {
                groupIds = context.getUserServer().findGroupsByName(groupNameToCreate);
            } catch (RemoteException e) {
                throw new FatalToolException("While fetching groups: " + e.getMessage(), e);
            }

            if (groupIds != null && groupIds.length > 0) {
                System.err.println(groupNameToCreate + " already exists.");
            }
            else {
                try {
                    group = context.getUserServer().createGroup(currentCaller.getUserId());

                    group.setName(groupNameToCreate, currentCaller);

                    System.out.println(AbstractPrincipalIdField.get(group.getGroupId(), context));
                } catch (Exception e) {
                    System.err.println("While creating group " + groupNameToCreate + ": " + e.toString());

                    continue;
                }
            }

            for (PrincipalId member : parameters.getMembers()) {
                try {
                    group.addMember(member, currentCaller);
                }
                catch (Exception e) {
                    System.err.println("While adding member " + AbstractPrincipalIdField.get(member, context) +
                        " to " + groupNameToCreate + ": " + e.toString());
                }
            }

            for (PrincipalId owner : parameters.getOwners()) {
                try {
                    group.addOwner(owner, currentCaller);
                }
                catch (Exception e) {
                    System.err.println("While adding owner " + AbstractPrincipalIdField.get(owner, context) +
                        " to " + groupNameToCreate + ": " + e.toString());
                }
            }
        }
    }

    public String getHelp() {
        return null;
    }

}
