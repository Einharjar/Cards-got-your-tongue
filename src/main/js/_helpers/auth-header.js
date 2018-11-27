//Auth header = hjalp function som retunerar http Autensiering -heder som innehaller JSON web Token(JWT)
// om den senaste inloggade anvandaren lagrad lokalt.Om anvandare ej är inloggad skickas ett tomt object
/*
export function authHeader() {
    // retunerar authorization header me jwt token
    let user = JSON.parse(localStorage.getItem('user'));

    if (user && user.token) {
        return { 'Authorization': 'Bearer ' + user.token };
    } else {
        return {};
    }
}

*/