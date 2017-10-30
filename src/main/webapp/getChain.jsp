<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page errorPage="errorPage.jsp"%>
<html>
<body>
<header>
    <p>
        <a href="index.jsp">Main</a>
        <a href="getChain.jsp">Get chain</a>
        <a href="generateSets.jsp">Generate sets</a>
    </p>
</header>
<div >
    <form name = "get_chain" method="post" action="DispatcherServlet?action=getChainFromPool">
        <label> Input count of domino
            <input type="number" name="amount_of_dominoes" value="" />
        </label>
        <input type="submit"  value="Get amount of domino" >
        <input type="submit"  value="Get random amount of domino" >
    </form>
</div>

<c:if test="${sessionScope.chain != null}">
    <h2><c:out value="${sessionScope.chain}" /></h2>
</c:if>
</body>
</html>
