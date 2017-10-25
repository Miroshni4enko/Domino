<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<body>
<header>
        <a href="index.jsp">Main</a>
        <a href="GetChain.jsp">Get chain</a>
        <a href="GenerateSets.jsp">Generate sets</a>
</header>
        <div >
                <form name = "generate_sets" method="post" action="DispatcherServlet?action=generateSets">
                        <input type="submit"  value="Get count of domino" >
                        <input type="submit"  value="Get random count of domino" >
                </form>
        </div>
        <c:if test="${sessionScope.chain != null}">
                <h2><c:out value="${sessionScope.chain}" /></h2>
        </c:if>
        <c:if test="${sessionScope.sets != null}">
                <h2>Sets:</h2>
                <c:forEach var="set" items="${sessionScope.all_sets}">
                        <p>${set}</p>
                </c:forEach>
        </c:if>
</body>
</html>
