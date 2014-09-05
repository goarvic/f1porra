package f1porra.f1.Clasification

import f1porra.GroupV
import f1porra.User

class UserClasificationSeason {

    int season
    GroupV group

    User user
    Date dateUpdated

    int points

    static constraints = {
        group nullable:false
        user nullable : false
        dateUpdated nullable : true
        user(unique : ['season', 'group'])
    }
}
