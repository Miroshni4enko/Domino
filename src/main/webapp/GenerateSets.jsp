<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page errorPage="errorPage.jsp"%>
<html>
<body>
<header>
    <a href="index.jsp">Main</a>
    <a href="GetChain.jsp">Get chain</a>
    <a href="GenerateSets.jsp">Generate sets</a>
</header>
    <div>
          <form name = "generate_sets" method="post" action="DispatcherServlet?action=generateSets">
            <input type="submit" name = "get_set" value="Get all sets" >
            <input type="submit" name = "get_set" value="Get the longest set" >
          </form>
    </div>
    <div>
          <c:if test="${sessionScope.chain != null}">
              <h2><c:out value="${sessionScope.chain}" /></h2>
          </c:if>
    </div>
    <div>
        <c:if test="${sessionScope.all_sets != null}">
                <h2>Sets:</h2>
                <c:forEach var="set" items="${sessionScope.all_sets}">
                        <p>${set}</p>
                </c:forEach>
        </c:if>
    </div>
    <div>
            <table>
            <caption>History:</caption>
                <tr>
                    <td>Date</td><td>Chain</td><td>Set</td>
                </tr>
                
                <c:if test="${sessionScope.history_sets != null}">
                     <c:forEach var="history" items="${sessionScope.history_sets}">
                     <tr>
                        <td>${history.date}</td><td>${history.chain}</td><td>${history.set}</td>
                     </tr>
                     </c:forEach>
                </c:if>
            </table>
    </div>
</body>
</html>
