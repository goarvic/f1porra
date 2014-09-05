package f1porra.f1


class GrandPrix {

    String name

    String circuitImageName

    int season
    int raceNumberInSeason

    DriverHierarchy raceResult
    DriverHierarchy qualyResult

    Driver fastLap

    Date finishPole
    Date raceStart
    Date qualyStart
    Date freePracticeStart



    def beforeValidate()
    {
        if ((finishPole == null) && (freePracticeStart != null))
        {
            finishPole = new Date(freePracticeStart.getTime())
        }

        if (((season == null) || (season == 0)) && (raceStart != null))
        {
            season = raceStart.getYear() + 1900
        }
    }


    static constraints = {
        //raceNumberInSeason nullable : true
        //season nullable : true
        circuitImageName nullable : true
        raceResult nullable : true
        qualyResult nullable : true
        fastLap nullable: true
        finishPole nullable : true
        raceStart nullable : false
        qualyStart nullable : false
        freePracticeStart nullable : false

        season(unique : ['raceNumberInSeason'])
    }


}
