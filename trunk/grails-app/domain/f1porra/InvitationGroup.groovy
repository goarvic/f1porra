package f1porra

class InvitationGroup {

    //GroupV groupInvitation
    Boolean isNew

    static belongsTo = [groupInvitation: GroupV, userInvited : User]

    static constraints = {
        groupInvitation nullable:false
        isNew nullable:true
    }
}
