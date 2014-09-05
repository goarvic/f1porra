package f1porra

import f1porra.f1.Driver
import f1porra.f1.DriverHierarchy
import f1porra.f1.GrandPrix

class Billing {


    DriverHierarchy raceBill
    DriverHierarchy qualyBill

    Driver fastLapBill

    GrandPrix grandPrix

    static belongsTo = [userBill : User]



    static constraints = {

        grandPrix(unique: ['userBill'])
    }
}
