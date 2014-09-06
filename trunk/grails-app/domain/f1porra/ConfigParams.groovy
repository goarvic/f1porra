package f1porra

class ConfigParams {

    String retrieveResultsURL
    String retrieveInfoGPs
    String retrieveDriversInfoURL

    static constraints = {
        retrieveResultsURL nullable : false
        retrieveInfoGPs nullable : false
        retrieveDriversInfoURL nullable : false
    }
}
