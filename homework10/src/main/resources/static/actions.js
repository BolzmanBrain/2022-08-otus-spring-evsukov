function getAuthors(actionAfter) {
	fetch('/api/v1/authors')
		.then(response => response.json())
		.then(json => actionAfter(json))
}

function getGenres(actionAfter) {
	fetch('/api/v1/genres')
		.then(response => response.json())
		.then(json => actionAfter(json))
}

async function getBookById (id) {
	book = await fetch('/api/v1/books/' + id)
		.then(response => response.json())
	return book
}

function outputAuthors(authors, book = null) {
	authors.forEach((author) => {
		selectedString = book == null || book.idAuthor != author.id ? '' : 'selected'

		$('#book-author-select').append(`
			<option value="${author.id}" ${selectedString}>${author.name}</option>
		`)
	});
}

function outputGenres(genres, book = null) {
	genres.forEach((genre) => {
		selectedString = book == null || book.idGenre != genre.id ? '' : 'selected'

		$('#book-genre-select').append(`
			<option value="${genre.id}" ${selectedString}>${genre.name}</option>
		`)
	});
}

function redirectToStartPage() {
	location.replace("/")
}

function getParamFromUrl(paramName){
    var url = document.location.href;
    var qs = url.substring(url.indexOf('?') + 1).split('&');
    for(var i = 0, result = {}; i < qs.length; i++){
        qs[i] = qs[i].split('=');
        result[qs[i][0]] = decodeURIComponent(qs[i][1]);
    }
    return result[paramName];
}
