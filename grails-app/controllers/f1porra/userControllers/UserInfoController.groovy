package f1porra.userControllers

import f1porra.User
import grails.converters.JSON

class UserInfoController {

    def springSecurityService

    def getNewInvitationsGroup()
    {
        User actualUser = User.get(springSecurityService.principal.id)

        def newInvitations = []

        actualUser.invitations.each{invitation->
            if (invitation.isNew)
            {
                newInvitations.add(invitation)
                invitation.isNew = false
                invitation.save(flush:true)
            }

        }

        render newInvitations as JSON

    }

    def getInvitations()
    {
        User actualUser = User.get(springSecurityService.principal.id)

        def invitations = []

        actualUser.invitations.each{invitation->
            invitations.add(invitation)
        }

        render invitations as JSON

    }


}
