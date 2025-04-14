var defaultLatitude = 37.5664056;
var defaultLongitude = 126.9778222;

var unknownLocation = '<div style="width: 264px; height: 62px; padding:5px;">현위치 정보를 가져올 수 없어요!<br>다른 지역을 선택해보세요☀';
var knownLocation = '<div style="width: 240px; height: 36px; padding:5px;">이곳의 날씨가 궁금하시나요?';
var currentLocation = '<div style="width: 200px; height: 36px; padding:5px;">여기에 계신가요?';

var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(defaultLatitude, defaultLongitude), // 지도의 중심좌표
        timeout: Infinity,
        level: 6 // 지도의 확대 레벨
    };

var map = new kakao.maps.Map(mapContainer, mapOption); // 지도를 생성합니다
var marker = new kakao.maps.Marker();
var infowindow = new kakao.maps.InfoWindow({
    removable : false
});

if($('#previous-lat').val() != 0 && $('#previous-lng').val() != 0) {

    var lat = $('#previous-lat').val(), // 위도
        lng = $('#previous-lng').val(); // 경도

    var locPosition = new kakao.maps.LatLng(lat, lng);
    displayMarker(locPosition, knownLocation);
} else if (navigator.geolocation) { // HTML5의 geolocation으로 사용할 수 있는지 확인합니다

    navigator.geolocation.getCurrentPosition(
        function (position) {
            var lat = position.coords.latitude, // 위도
                lng = position.coords.longitude; // 경도

            var locPosition = new kakao.maps.LatLng(lat, lng);

            displayMarker(locPosition, currentLocation);
        },
        function (error) {
            console.error("Geolocation error: ", error);

            var locPosition = new kakao.maps.LatLng(defaultLatitude, defaultLongitude);

            displayMarker(locPosition, unknownLocation);
        },
        {
            enableHighAccuracy: false, // 높은 정확도 요청 여부 (true일 경우 배터리 소모↑)
            timeout: 5000, // 5초 후 타임아웃
            maximumAge: 0 // 캐시된 위치 사용 안 함
        }
    );

} else { // GeoLocation을 사용할 수 없을 때.

    var locPosition = new kakao.maps.LatLng(defaultLatitude, defaultLongitude);

    displayMarker(locPosition, unknownLocation);
}

function invalidLocation() {
    var locPosition = new kakao.maps.LatLng(defaultLatitude, defaultLongitude);

    displayMarker(locPosition, knownLocation);
}

// 지도에 클릭 이벤트를 등록합니다
// 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

    // 클릭한 위도, 경도 정보를 가져옵니다
    var locPosition = mouseEvent.latLng;
    infowindow.close();
    displayMarker(locPosition, knownLocation);

});

// 지도에 마커와 인포윈도우를 표시하는 함수입니다
function displayMarker(locPosition, message) {
    currentLocPosition(locPosition);

    marker.setMap(null);
    marker.setMap(map);
    marker.setPosition(locPosition);

    var iwContent = message + '</div>'; // 인포윈도우에 표시할 내용

    infowindow.setContent(iwContent);

    // 인포윈도우를 마커위에 표시합니다
    infowindow.open(map, marker);

    // 지도 중심좌표를 변경합니다
    map.setCenter(locPosition);

    // 경도, 위도를 nx, ny값으로 변환합니다.
    var xy_result = dfs_xy_conv('toXY', locPosition.getLat(), locPosition.getLng());

    sendNxNy(xy_result);

    var message2 = '클릭한 위치의 nx는 ' + xy_result.x + ' 이고, ';
    message2 += 'ny는 ' + xy_result.y + ' 입니다';
    var resultDiv2 = document.getElementById('clickNxNy');
    resultDiv2.innerHTML = message2;
}

function currentLocPosition(locPosition) {
    $('#form-nx').val(locPosition.getLat());
    $('#form-ny').val(locPosition.getLng());
}

