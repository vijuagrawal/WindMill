<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>

</html>
<form action="/user/registration/add" method="post">


	<p>
		<label for="username">UserName</label> <input type="email"
			id="username" name="username" required />
	</p>
	<p>
		<label for="firstName">firstName</label> <input type="text"
			id="firstName" name="firstName" />
	</p>
	<p>
		<label for="lastName">LastName</label> <input type="text"
			id="lastName" name="lastName" />
	</p>
	<p>
		<label for="password">Password</label> <input type="password"
			id="password" name="password" onchange="validatePassword()" required />
	</p>
	<p>
		<label for="password">Confirm Password</label> <input type="password"
			id="cpassword" name="cpassword" onKeyUp="validatePassword()" required />
	</p>
	<input type="hidden" />
	<button type="submit" class="btn">Sign Up</button>

</form>
<script>
	
	function validatePassword() {
		var password = document.getElementById("password");
		var confirm_password = document.getElementById("cpassword");

		if (password.value != confirm_password.value) {
			confirm_password.setCustomValidity("Passwords Don't Match");
		} else {
			confirm_password.setCustomValidity('');
		}
	}
</script>
</html>