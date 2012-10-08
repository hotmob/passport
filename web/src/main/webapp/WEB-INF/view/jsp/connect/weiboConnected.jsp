<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>

<h3>Connected to Weibo</h3>

<form id="disconnect" method="post">
	<div class="formInfo">
		<p>766.com application is already connected to your Weibo account.
			Click the button if you wish to disconnect.			
	</div>

	<button type="submit">Disconnect</button>	
	<input type="hidden" name="_method" value="delete" />
</form>

