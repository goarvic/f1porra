package f1porra

class PreUser {

    String email

    Date dateCreated

    GroupV initialGroup

    String keyValidate


    def beforeInsert() {
        dateCreated = new Date()
    }


    static constraints = {
        keyValidate nullable : true
        email nullable : false, unique: true
        dateCreated nullable: true
        initialGroup nullable: false
    }
}
