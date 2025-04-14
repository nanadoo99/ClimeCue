// nx와 ny값을 기상청 api에 전송한다.
function sendNxNy(xy_result) {
    emptyValues();

    $.ajax({
        url: '/public/home',
        type: 'GET',
        data: {nx : xy_result.x, ny : xy_result.y},
        dataType: 'json',
        success: function(data) {
            console.log('데이터:', data);
            completeWeatherData(data.weatherData);
            completeOpenAIResponse(data.openAIResponse.message);
            completePersonalCondition(data.personalConditionDto);
        },
        error: function(xhr) {
            var res = xhr.responseJSON;

            if (!res) {
                try {
                    res = JSON.parse(xhr.responseText);
                } catch(e) {
                    res = {};
                }
            }

            if (res && res.code === "VF_01") {
                invalidLocation();
            }

            alert("Code: " + res.code + "\nMessage: " + res.message);
        }
    });
}

function emptyValues() {
    $('#weatherData').empty();
    $('#openAIResponse').empty();
    $('#age').val("");
    $('#bodyType').val("");
    $('#hobby').val("");
    $('#exercise').val("");
    $('#medical').val("");
}

function completeWeatherData(weatherData) {
    var categories = ["PTY", "REH", "RN1", "T1H", "WSD"];

    // weatherData.vilageFcstUltraSrtItems 배열이 존재하는지 체크합니다.
    var items = weatherData.vilageFcstUltraSrtItems || [];

    categories.forEach(function(keyword) {
        // 해당 카테고리의 데이터를 필터링합니다.
        var filteredData = items.filter(function(item) {
            return item.category === keyword;
        });

        // 필터링된 데이터가 있다면 HTML 요소에 추가합니다.
        filteredData.forEach(function(data) {
            $('#weatherData').append('<p>' + data.categoryName + ': ' + data.obsrValueWithUnit + '</p>');
        });
    });
}

function completeOpenAIResponse(message) {
    $('#openAIResponse').append(message != null? message.content : "");
}

function completePersonalCondition(data) {
    $('#age').val(data.age);
    $('#bodyType').val(data.bodyType);
    $('#hobby').val(data.hobby);
    $('#exercise').val(data.exercise);
    $('#medical').val(data.medical);
}