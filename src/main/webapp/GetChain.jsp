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
    <form name = "buy_form" method="post" action="?action=getChainFromPool">
        <label> Input count of domino
            <input type="number" name="count_of_dominoes" value="" />
        </label>
        <input type="submit"  value="Get count of domino" >
        <input type="submit"  value="Get random count of domino" >
    </form>
</div>
<h2><c:out value="${sessionScope.chain}" /></h2>
</body>
</html>
