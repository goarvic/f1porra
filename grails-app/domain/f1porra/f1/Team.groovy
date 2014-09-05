package f1porra.f1

class Team {

    String name
    String logoName



    static constraints = {
        name nullable: false
        name unique: true
        logoName nullable : true
    }
}
