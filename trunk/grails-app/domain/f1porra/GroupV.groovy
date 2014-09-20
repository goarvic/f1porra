package f1porra

import f1porra.f1.Clasification.UserClasificationGP

class GroupV {

    String name

    String nameImageGroup

    User owner

    Date dateCreated

    static hasMany = [invitations : InvitationGroup, users : User, userClasifications : UserClasificationGP]



    static mappedBy = [users: "groupsInside"]
    static belongsTo = User

    def beforeInsert() {
        dateCreated = new Date()
    }

    static constraints = {
        userClasifications nullable : true
        owner nullable: true
        name nullable : false
        nameImageGroup nullable : true
    }


}
