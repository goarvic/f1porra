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
    <title>VGA F1 Billings - <g:message code="default.classification.title"/></title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css',file: 'datatable.css')}" />
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootbox.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/tables',file: 'datatable.js')}"></script>
</head>

<body>

<script type=text/javascript>
    $(document).ready(function () {

        <g:each in="${actualUser.groupsInside}" var="group">
            $("#table_${group.name}").dataTable({
                "iDisplayLength": 25,
                "bPaginate": false,
                "sPaginationType": "full_numbers",
                "aaSorting": [[ ${grandPrixesSeason.size()+1}, "desc" ]]
            });

        </g:each>

        <g:if test="${actualUser.groupsInside.size() != 0}">
            $('#table_${actualUser.groupsInside[0].name}').show()
            $("#button_${actualUser.groupsInside[0].name}").addClass("active")
        </g:if>



    });
    <g:each in="${actualUser.groupsInside}" var="group">
        $(document).on( 'click', '#button_${group.name}',
                function(){

                    //$('#btn-primary').removeClass("#btn-primary")

                    <g:each in="${actualUser.groupsInside}" var="group_formulary">
                        $('#table_${group_formulary.name}').hide()
                        $("#button_${group_formulary.name}").removeClass("active")
                    </g:each>

                    $('#table_${group.name}').show()
                    $("#button_${group.name}").addClass("active")

                    /*
                    var buttonRace = document.getElementById("raceButtonForm")
                    var buttonQualy = document.getElementById("qualyButtonForm")


                    buttonRace.setAttribute("class", "btn btn-default")
                    buttonQualy.setAttribute("class", "btn btn-default")

                    $('#raceForm').hide()
                    $('#qualyForm').hide()
                    $('#fastLapForm').show()

                    var fastLapButton = document.getElementById("fastLapButtonForm")
                    fastLapButton.setAttribute("class", "btn btn-primary")  */
                }
        );
    </g:each>




</script>



<div id="page-body" role="main" class="container">

    <div class="panel panel-default">

        <div class="panel-heading">
            <h3 class="panel-title"><g:message code="default.classification.title"/></h3>
        </div>


        <ul class="nav nav-tabs">
            <g:each in="${actualUser.groupsInside}" var="group">
                <li id="button_${group.name}"><a href="#">${group.name}</a></li>
            </g:each>
        </ul>


        <div class="panel-body">
            <g:each in="${actualUser.groupsInside}" var="group">
                <table cellpadding="0" cellspacing="0" border="0" class="display dTable" id="table_${group.name}" style="display:none">
                    <thead>
                    <tr>
                        <th><g:message code="default.classification.player"/></th>
                        <g:each in="${grandPrixesSeason}" var="grandPrix">
                            <th style="font-size: 80%;" data-toggle="tooltip" data-placement="top" title="${grandPrix.name}">${grandPrix.raceNumberInSeason}</th>
                        </g:each>
                        <th>Total</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${group.users}" var="user">
                        <tr class="gradeA odd">
                            <td>${user.name}</td>
                            <g:set var="totalPoints" value="${0}"/>
                            <g:each in="${grandPrixesSeason}" var="grandPrix">
                                <g:set var="found" value="${0}"/>
                                <g:set var="iteratorUserClassif" value="${0}"/>
                                <g:while test="${(found==0) && (iteratorUserClassif < group.userClasifications.size())}" in="${group.userClasifications}" expr="(it.user.id == user.id)&&(it.grandPrix.id == grandPrix.id)">
                                    <g:if test="${(group.userClasifications[iteratorUserClassif].user.id == user.id) && (group.userClasifications[iteratorUserClassif].grandPrix.id == grandPrix.id)}">
                                        <g:set var="totalPoints" value="${totalPoints + group.userClasifications[iteratorUserClassif].points}"/>
                                        <g:set var="found" value="${1}"/><g:set var="foundBilling" value="${0}"/><g:set var="itBillings" value="${0}"/>
                                        <g:while test="${(foundBilling == 0) && (itBillings<user.billings.size())}">
                                            <g:if test="${user.billings[itBillings].grandPrix.id == grandPrix.id}">
                                                <g:set var="foundBilling" value="${1}"/>
                                                <td><a href="${createLink(controller: "billings", action: "voteResultsOU", params : [gpId : grandPrix.id, userId : user.id])}">${group.userClasifications[iteratorUserClassif].points}</a></td>
                                            </g:if>
                                            <g:set var="itBillings" value="${itBillings+1}"/>
                                        </g:while>
                                        <g:if test="${foundBilling == 0}">
                                            <td>${group.userClasifications[iteratorUserClassif].points}</td>
                                        </g:if>
                                    </g:if>
                                    <g:set var="iteratorUserClassif" value="${iteratorUserClassif+1}"/>
                                </g:while>
                                <g:if test="${found == 0}"><td>-</td></g:if>
                            </g:each>
                            <td>${totalPoints}</td>
                        </tr>
                    </g:each>


                    <%--<tr class="gradeA odd">
                        <td>prueba1</td>
                        <td>prueba2</td>
                    </tr>--%>
                    <%--<g:each in="${teams}" var="team" status="i">
                        <tr class="gradeA ${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${team.name}</td>
                            <td>Not Available Yet</td>
                        </tr>
                    </g:each>--%>
                    </tbody>
                </table>
            </g:each>
        </div>
    </div>

</div>
</body>
</html>