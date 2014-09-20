package f1porra.userControllers

import f1porra.GroupV
import f1porra.InvitationGroup
import f1porra.User
import grails.converters.JSON

class ProfileController {

    def springSecurityService
    def adminF1Service
    def clasificationService

    def index()
    {
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        /*def c = GroupV.createCriteria()
        def groups = c.list {
            users {
                eq('id', actualUser.id)
            }
        }*/

        render(view: "viewProfile", model: [actualUser : actualUser])
    }

    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************

    def leaveGroup()
    {
        User actualUser = User.get(springSecurityService.getPrincipal().id)
        def groupId = params.groupId
        groupId = groupId.toInteger()

        GroupV groupToLeave = GroupV.findById(groupId)
        if (groupToLeave.owner == actualUser)
        {
            flash.error = "You can't leave from group created by you"
            redirect(controller: "profile", action: "index")
        }
        else
        {
            def c = GroupV.createCriteria()
            def groups = c.list {
                and{
                    eq('id', groupToLeave.id)
                    users {
                        eq('id', actualUser.id)

                    }
                }
            }
            if (groups.size() <= 0)
            {
                flash.error = "You actually are not in this group"
                redirect(controller: "profile", action: "index")
            }
            else
            {
                groupToLeave.removeFromUsers(actualUser)

                if (groupToLeave.save(flush: true) == null)
                {
                    flash.error = "Unexpected error"
                    redirect(controller: "profile", action: "index")
                }
                else
                {
                    flash.message = "Success!"
                    redirect(controller: "profile", action: "index")
                }
            }

        }

    }

    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************

    def createGroup()
    {
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        GroupV newGroup = GroupV.findByNameAndOwner(params.groupName, actualUser)
        if (newGroup != null)
        {
            flash.error = message(code: "default.profile.selectOtherName", args: [])
            redirect(controller: "profile", action: "index")
            return
        }

        newGroup = new GroupV(name : params.groupName, owner: actualUser)
        if (newGroup.save(flush:true) == null)
        {
            log.info "error: " + newGroup.errors
            flash.error = message(code: "default.profile.unknownError", args: [])
        }

        else
        {
            actualUser.addToGroupsInside(newGroup).save(flush:true)

            def actualSeason = new Date().getYear() + 1900
            clasificationService.processGPsUsersForGroup(actualSeason, newGroup)
            flash.message = message(code: "default.profile.groupCreated", args: [])
        }

        redirect(controller: "profile", action: "index")
    }



    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************


    def inviteUserToGroup()
    {
        User actualUser = User.get(springSecurityService.getPrincipal().id)
        def groupName = params.groupName
        def userMail = params.userMail



        User user = User.findByEmail(userMail)
        if (user == null)
        {
            adminF1Service.inviteUserToSystem(userMail, groupName)
        }
        else
        {
            GroupV groupToInvite = GroupV.findByName(groupName)

            log.info "-" + groupName + "-"


            if ((groupToInvite != null) && (actualUser != user))
            {
                InvitationGroup newInvitation = new InvitationGroup(groupInvitation: groupToInvite, isNew: true)
                newInvitation.save(flush:true)

                user.addToInvitations(newInvitation).save(flush:true)
                flash.message = message(code: "default.profile.userInvited", args: [])
            }
            else
            {
                flash.error = "Invalid group or email"
            }

        }


        redirect(controller: "profile", action: "index")
    }

    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************

    def ajaxInviteUserToGroup()
    {
        User actualUser = User.get(springSecurityService.getPrincipal().id)
        def groupName = params.groupName
        def userMail = params.userMail

        User user = User.findByEmail(userMail)
        def response

        if (user == null)
        {
            adminF1Service.inviteUserToSystem(userMail, groupName)
        }
        else
        {
            GroupV groupToInvite = GroupV.findByName(groupName)

            if ((groupToInvite != null) && (actualUser != user))
            {
                InvitationGroup newInvitation = new InvitationGroup(groupInvitation: groupToInvite, isNew: true)
                newInvitation.save(flush:true)

                user.addToInvitations(newInvitation).save(flush:true)

                response = "{\"status\" : \"success\"}"
            }
            else
            {
                response = "{\"status\" : \"success\"}"
            }
        }


        render response
    }



    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************

    def acceptInvitation()
    {
        def invitationId = params.invitationId
        invitationId = invitationId.toInteger()
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        InvitationGroup invitation = InvitationGroup.findById(invitationId)


        //Tenemos que comprobar que este usuario ha sido invitado
        def find = -1
        def iteratorInvitations = 0
        while ((find != 1) && (iteratorInvitations < actualUser.invitations.size()))
        {
            if (actualUser.invitations[iteratorInvitations].id == invitationId)
                find = 1
            iteratorInvitations++
        }
        if (find != 1)
        {
            flash.error = "No invitation has been found."
            redirect(controller: "profile", action: "index")
        }
        else
        {
            def groupToAddUser = invitation.groupInvitation
            actualUser.addToGroupsInside(groupToAddUser)
            if (actualUser.save(flush: true) == null)
            {
                flash.error = "Unexpected Error"
                redirect(controller: "profile", action: "index")
            }
            else
            {
                actualUser.removeFromInvitations(invitation)
                groupToAddUser.removeFromInvitations(invitation)

                invitation.delete(flush: true)
                flash.message = message(code: "default.profile.invitationAccepted", args: [])

                def actualSeason = new Date().getYear() + 1900
                clasificationService.processGPsUsersForGroup(actualSeason, groupToAddUser)

                redirect(controller: "profile", action: "index")
            }
        }
    }


    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************

    def declineInvitation()
    {
        def invitationId = params.invitationId
        invitationId = invitationId.toInteger()
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        InvitationGroup invitation = InvitationGroup.findById(invitationId)


        //Tenemos que comprobar que este usuario ha sido invitado
        def find = -1
        def iteratorInvitations = 0
        while ((find != 1) && (iteratorInvitations < actualUser.invitations.size()))
        {
            if (actualUser.invitations[iteratorInvitations].id == invitationId)
                find = 1
            iteratorInvitations++
        }
        if (find != 1)
        {
            flash.error = "No invitation has been found."
            redirect(controller: "profile", action: "index")
        }
        else
        {
            invitation.delete(flush: true)
            flash.message = message(code: "default.profile.invitationRejected", args: [])
            redirect(controller: "profile", action: "index")
        }
    }



    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************

    def deleteGroup()
    {
        def groupId = params.groupId.toInteger()
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        GroupV groupToDel = GroupV.findById(groupId)
        if (groupToDel == null)
        {
            flash.error = "No group found with this ID"
        }
        else
        {
            if (groupToDel.owner != actualUser)
            {
                flash.error = "You can't delete this group beacause you are not the owner."
            }
            else
            {
                def usersInsideGroup = groupToDel.users
                def groupOfUsersToRemove = []
                usersInsideGroup.each {userInside->
                    groupOfUsersToRemove.add(userInside)
                }

                groupOfUsersToRemove.each{user->
                    user.removeFromGroupsInside(groupToDel)
                    user.save(flush:true)
                }

                actualUser.removeFromGroupsOwn(groupToDel)
                actualUser.save(flush:true)

                groupToDel.delete(flush:true)
                flash.message = "Success removing group"
            }
        }
        redirect(controller: "profile", action: "index")
    }


    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************
    //************************************************************************************************************

    def modifyUserData()
    {
        User actualUser = User.get(springSecurityService.getPrincipal().id)

        def requestParams = request.JSON


        if (requestParams.emailNotifications == true)
        {
            actualUser.mailNotifications = true
        }
        else
        {
            actualUser.mailNotifications = false
        }

        actualUser.email = requestParams.userMail
        actualUser.name = requestParams.displayName

        if (actualUser.save(flush:true) == null)
        {
            log.error "Error saving new user details " + actualuser.errors
            render actualUser.errors as JSON
        }
        else
            render '{"status" :"success"}'
    }
}
