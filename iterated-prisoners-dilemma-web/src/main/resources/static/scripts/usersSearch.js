$(function () {
    $('#searchBox').on('input', search);
    // $('#searchButton').click(search);
    // $('#searchBox').keyup(function (e) {
    //     if (e.keyCode === 13) {
    //         $('#searchButton').click();
    //     }
    // });
});

function search() {
    let searchWord = $('#searchBox').val();
    //console.log(searchWord);
    $("#users").load("/users/search", {searchWord: searchWord});
}

