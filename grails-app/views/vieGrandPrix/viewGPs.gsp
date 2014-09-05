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
    <title>VGA F1 Billings - Drivers</title>
    <meta name="layout" content="main"/>
    <link rel="stylesheet" href="${resource(dir: 'css',file: 'datatable.css')}" />
    <script type="text/javascript" src="${resource(dir: 'js', file: 'bootbox.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js/tables',file: 'datatable.js')}"></script>
</head>

<body>

<script type=text/javascript>
    $(document).ready(function () {

        $('#addDriver').on('click', function() {
            window.location = "${createLink(action: 'driverAddForm', controller: 'drivers')}"
        });





    });
</script>



<div id="page-body" role="main" class="container">


    <div class="panel panel-default">

        <div class="panel-heading">
            <h3 class="panel-title">F1 Drivers List</h3>
        </div>
        <div class="panel-body">

            <button id="addDriver" type="button" class="btn btn-default">
            <sec:ifAllGranted roles="ROLE_ADMIN">
                <span class="glyphicon glyphicon-chevron-right"></span>Add New f1.DriverV0</button>
            </sec:ifAllGranted>
            <table cellpadding="0" cellspacing="0" border="0" class="display dTable" id="tableDrivers">
                <thead>
                <tr>
                    <th>DriverName</th>
                    <%--<th>Role</th>--%>
                    <th>Team</th>
                    <th>Options</th>
                </tr>
                </thead>
                <tbody>
                <g:each in="${drivers}" var="driver" status="i">
                    <tr class="gradeA ${(i % 2) == 0 ? 'odd' : 'even'}">
                        <td>${driver.name}</td>
                        <td>${driver.team.name}</td>
                        <td>Not Available Yet</td>
                    </tr>
                </g:each>
                </tbody>
            </table>




        </div>
    </div>

</div>
</body>
</html>