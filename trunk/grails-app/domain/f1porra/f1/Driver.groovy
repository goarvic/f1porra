package f1porra.f1

class Driver {

    String name
    Team team

    String state
    String imageName




    static constraints = {
        name nullable : false
        team nullable : false
        state nullable : false
        imageName nullable : true
    }
}
