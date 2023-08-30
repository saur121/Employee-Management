/*
const search = () => {
    let query = $("#search-input").val();
    

    if (query == '') {
        $(".search-result").hide();
    } else {
		
		    console.log(query);
		
        let url = `http://localhost:8080/search/${query}`;

        fetch(url)
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
                updateSearchResults(data);
            })
            .catch((error) => {
                console.error('Fetch error:', error);
                $(".search-result").html("Error occurred while fetching data");
                $(".search-result").show();
            });
    }
};

const updateSearchResults = (data) => {
    let resultHTML = '<div class="list-group">';
    
    data.forEach((employee) => {
        resultHTML += `<a href="#" class="list-group-item list-group-action">${employee.fullName}</a>`;
    });
    
    resultHTML += '</div>';
    
    $(".search-result").html(resultHTML);
    $(".search-result").show();
};
*/

const search = () => {
    let query = $("#search-input").val();

    if (query === "") {
        $(".search-result").hide();
    } else {
        console.log(query);

        let url = `http://localhost:8080/nativeQuery/${encodeURIComponent(query)}`;

        fetch(url)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log(data);
            })
            .catch((error) => {
                console.error("Fetch error:", error);
            });

        $(".search-result").show();
    }
};


