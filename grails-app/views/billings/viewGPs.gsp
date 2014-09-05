<%--
  Created by IntelliJ IDEA.
  User: goarvic
  Date: 9/12/13
  Time: 11:48
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>


    <title>VGA F1 Billings - GPs</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css',file: 'datatable.css')}" />
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootbox.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/tables',file: 'datatable.js')}"></script>
    <link rel="stylesheet" href="${resource(dir: 'css',file: 'jquery.countdown.css')}" type="text/css">
    <script type="text/javascript" src="${resource(dir: 'js',file: 'jquery.plugin.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js',file: 'jquery.countdown.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js',file: 'date.js')}"></script>
</head>

<body>

<script type=text/javascript>
    $(document).ready(function () {

        <g:if test="${nextGP != null}">
            var nextRaceDate = new Date(${nextGP.finishPole.getTime()});
            $('#defaultCountdown').countdown({until: nextRaceDate});
        </g:if>
        <%--$('#addTeam').on('click', function() {
            window.location = "${createLink(action: 'teamAddForm', controller: 'teams')}"
        });--%>


        for (var i=1; i<5; i++)
        {
            var idDateDiv = "#date_" + i
            var miliseconds = parseInt($(idDateDiv).html())
            var newDate = new Date(miliseconds/* + n*60*1000*/)
            var dateString = newDate.toString("yyyy-MM-dd HH:mm");
            $(idDateDiv).html(dateString)
        }
    });




</script>



<div id="page-body" role="main" class="container">

    <div class="col-sm-8">
        <div class="panel panel-default">

            <div class="panel-heading">
                <h3 class="panel-title">F1 Grand Prix List</h3>
            </div>
            <div class="panel-body">


                <sec:ifAllGranted roles="ROLE_ADMIN">
                    <button id="addTeam" type="button" class="btn btn-default"><span class="glyphicon glyphicon-chevron-right"></span>Add New GP</button>
                </sec:ifAllGranted>
                <table cellpadding="0" cellspacing="0" border="0" class="display dTable" id="tableDrivers">
                    <thead>
                    <tr>
                        <th>GP Name</th>

                        <th>Date</th>
                        <th>Options</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${gpList}" var="gp" status="i">
                        <tr class="gradeA ${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${gp.name}</td>
                            <td><g:formatDate format="yyyy-MM-dd" date="${gp.finishPole}"/></td>
                            <g:if test="${nowTime < gp.finishPole}">
                                <td><a href="${createLink(controller: "billings", action: "voteFormulary")}?gpId=${gp.id}"><g:message code="default.billings.vote"/></a></td>
                            </g:if>
                            <g:else>
                                <g:set var="voted" value="${0}"/>
                                <g:each in="${userBillings}" var="userBilling">
                                    <g:if test="${userBilling.grandPrix == gp}">
                                        <g:set var="voted" value="${1}"/>
                                        <td><a href="${createLink(controller: "billings", action: "voteResults")}?gpId=${gp.id}"><g:message code="default.billings.viewResults"/></a></td>
                                    </g:if>
                                </g:each>
                                <g:if test="${voted == 0}">
                                    <td><g:message code="default.billings.noVoted"/></td>
                                </g:if>

                            </g:else>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

    <div class="col-sm-4">
        <div class="nextGPBox">
            <h2><g:message code="default.billings.nextGrandPrix"/></h2>
            <g:if test="${nextGP != null}">
                <h3><a href="${createLink(controller: "billings", action: "voteFormulary")}?gpId=${nextGP.id}">${nextGP.name}</a></h3>
                <h3><g:message code="default.billings.raceSummary"/></h3>
                <div class="summaryRaceRow row">
                    <div class="col-md-6">
                        <span class="glyphicon glyphicon-flag"></span>
                        <g:message code="default.billings.freePracticeStart"/>:
                    </div>
                    <div class="col-md-6">
                        <div id="date_1">${nextGP.freePracticeStart.getTime()}</div>
                    </div>
                </div>
                <div class="summaryRaceRow row">
                    <div class="col-md-6">
                        <span class="glyphicon glyphicon-flag"></span>
                        <g:message code="default.billings.clasificationStart"/>:
                    </div>
                    <div class="col-md-6">
                        <div id="date_2">${nextGP.qualyStart.getTime()}</div>
                    </div>
                </div>
                <div class="summaryRaceRow row">
                    <div class="col-md-6">
                        <span class="glyphicon glyphicon-flag"></span>
                        <g:message code="default.billings.raceStart"/>:
                    </div>
                    <div class="col-md-6">
                        <div id="date_3">${nextGP.raceStart.getTime()}</div>
                    </div>
                </div>
                <div class="summaryRaceRow row">
                    <div class="col-md-6">
                        <span class="glyphicon glyphicon-flag"></span>
                        <g:message code="default.billings.finshVotePeriod"/>:
                    </div>
                    <div class="col-md-6">
                        <div id="date_4">${nextGP.finishPole.getTime()}<%--<g:formatDate format="yyyy-MM-dd HH:mm" date="${nextGP.finishPole}"/>--%></div>
                    </div>
                </div>
                </div>

                <div class="countdownRace">
                    <div id="defaultCountdown" class="countdownRace"></div>
                </div>
            </g:if>




    </div>



</div>
</body>
</html>