package f1porra

import f1porra.f1.Clasification.UserClasificationGP
import f1porra.f1.DriverHierarchy

class User {

    transient springSecurityService

    String username
    String name
    String surname
    String email
    Boolean mailNotifications
    String preferredLanguageCode

    static hasMany = [invitations : InvitationGroup, userClasifications : UserClasificationGP, groupsOwn : GroupV, groupsInside: GroupV, billings : Billing]

    static mappedBy = [groupsOwn: "owner", groupsInside: "users"]

    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    static transients = ['springSecurityService']

    static constraints = {
        preferredLanguageCode nullable : true
        mailNotifications nullable : true
        groupsInside nullable: true
        groupsOwn nullable: true
        name nullable:true, blank: true
        surname nullable: true, blank : true
        username blank: false, unique: true
        password blank: false
        email nullable : true
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Role> getAuthorities() {
        UserRole.findAllByUser(this).collect { it.role } as Set
    }

    def beforeInsert() {
        encodePassword()
        mailNotifications = true
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }

    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}
