<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>VGA F1 Billings - Manage GPs</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css',file: 'datatable.css')}" />
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootbox.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/tables',file: 'datatable.js')}"></script>
</head>

<body>

<script type=text/javascript>
    $(document).ready(function () {

        $('#addTeam').on('click', function() {
            window.location = "${createLink(action: 'teamAddForm', controller: 'teams')}"
        });





    });
</script>



<div id="page-body" role="main" class="container">

    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Process GP's calendar from URL F1 Official Page</h3>
        </div>
        <g:form controller="manageGrandPrix" action="addSeasongGPsFromURL" method="POST" enctype="multipart/form-data" name="urlGPsInformationForm">
            <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-6 col-md-6">
                            <div class="form-group">
                                <label for="url">URL of GP's Information</label>
                                <a href="#" class="tooltipDemo" data-toggle="tooltip" title="F1 Team Name."></a>
                                <g:if test="${configParams != null}">
                                    <input class="form-control required_input" type="text" name="url" id="url" value="${configParams.retrieveInfoGPs}">
                                </g:if>
                                <g:else>
                                    <input class="form-control required_input" type="text" name="url" id="url" value="">
                                </g:else>
                            </div>
                        </div>
                    </div>
            </div>
            <div class="panel-footer">
                <button type="submit" class="btn btn-default" id="submitUser">Submit</button>
            </div>
        </g:form>
    </div>



    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Retrieve GP's results from URL F1 Official Page</h3>
        </div>
        <g:form controller="manageGrandPrix" action="processResults" method="POST" enctype="multipart/form-data" name="urlGPsResultsForm">
            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-6 col-md-6">
                        <div class="form-group">
                            <label for="urlResults">URL of GP's Results</label>
                            <a href="#" class="tooltipDemo" data-toggle="tooltip" title="F1 Team Name."></a>
                            <g:if test="${configParams != null}">
                                <input class="form-control required_input" type="text" name="urlResults" id="urlResults" value="${configParams.retrieveResultsURL}">
                            </g:if>
                            <g:else>
                                <input class="form-control required_input" type="text" name="urlResults" id="urlResults" value="">
                            </g:else>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <button type="submit" class="btn btn-default" id="submitURLResults">Submit</button>
            </div>
        </g:form>
    </div>



    <div class="panel panel-default">
        <div class="panel-heading">
            <h3 class="panel-title">Process Classification Points Of Users</h3>
        </div>
        <g:form controller="manageGrandPrix" action="processClassificationPointsPerUser" method="POST" enctype="multipart/form-data" name="urlGPsResultsForm">
            <div class="panel-body">
                <button type="submit" class="btn btn-default" id="submitProcessClassification">Submit</button>
            </div>
        </g:form>

    </div>





</div>
</body>
</html>