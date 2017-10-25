<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<header>
    <p>
        <a href="index.jsp">Main</a>
        <a href="GetChain.jsp">Get chain</a>
        <a href="GenerateSets.jsp">Generate sets</a>
    </p>
</header>
<div >
    <form name = "get_chain" method="post" action="DispatcherServlet?action=getChainFromPool">
        <label> Input count of domino
            <input type="number" name="count_of_dominoes" value="" />
        </label>
        <input type="submit"  value="Get count of domino" >
        <input type="submit"  value="Get random count of domino" >
    </form>
</div>
<c:if test="${sessionScope.chain == null}">
    Cool
</c:if>
<%=session.getAttribute("chain")%>
<c:out value='${sessionScope.chain}'/>
</body>
</html>
