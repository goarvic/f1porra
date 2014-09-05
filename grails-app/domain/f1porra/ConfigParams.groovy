package f1porra

class ConfigParams {

    String retrieveResultsURL
    String retrieveInfoGPs

    static constraints = {
        retrieveResultsURL nullable : false
        retrieveInfoGPs nullable : false
    }
}
