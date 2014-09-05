package f1porra.f1.Clasification

import f1porra.GroupV
import f1porra.User
import f1porra.f1.GrandPrix

class UserClasificationGP {



    GrandPrix grandPrix


    Date dateUpdated

    int points
    static belongsTo = [group : GroupV, user : User]

    def beforeUpdate()
    {
        dateUpdated = new Date()
    }

    def beforeInsert()
    {
        dateUpdated = new Date()
    }

    static constraints = {
        grandPrix nullable : false
        group nullable : false
        user nullable: false
        dateUpdated nullable : true
    }
}
