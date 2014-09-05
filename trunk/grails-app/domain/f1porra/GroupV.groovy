package f1porra

import f1porra.f1.Clasification.UserClasificationGP

class GroupV {

    String name

    String nameImageGroup

    User owner

    static hasMany = [invitations : InvitationGroup, users : User, userClasifications : UserClasificationGP]



    static mappedBy = [users: "groupsInside"]
    static belongsTo = User



    static constraints = {
        userClasifications nullable : true
        owner nullable: true
        name nullable : false, unique: true
        nameImageGroup nullable : true
    }


}
