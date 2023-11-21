(function(){
    // span 요소 가져오기
    const spanEl = document.querySelector("main h2 span");
    // 화면에 표시할 문장 배열
    const txtArr=['pit-a-pat','memorable','romantic','fabulous','charming','twinkle','serendipity'];
    // 배열 인덱스 초기값
    let index=0;
    //  화면에 표시할 문장 배열에서 요소를 가져온 뒤 배열로 만들기
    let currentTxt = txtArr[index].split("");
    
    function writeTxt(){
        // shift() : 맨 앞의 요소를 추출하고 추출한 요소는 원본 배열에서 삭제
        spanEl.textContent+=currentTxt.shift();
    
        // currentTxt에 할당된 ㅐㅂ열의 길이가 0인지 확인함
        // 0이 아니라면 출력해야되는 단어가 남아있다는 뜻
        // setTimeout() : 두번째 인자는 = 시간 => 작성되는 글자 속도 매번 다르게 설정
        if(currentTxt.length!==0){
            setTimeout(writeTxt, Math.floor(Math.random()*100));
    
        // 배열 all 출력 후
        }else{
            currentTxt = spanEl.textContent.split("");   
            setTimeout(deleteTxt, 3000);
        }
    
    }
    writeTxt();
    
    // 텍스트 삭제 효과 구현
    function deleteTxt(){
        // pop() 메서드로 배열 요소를 끝에서부터 한개씩 삭제시킴
        currentTxt.pop();
        // 현재 배열에 있는 요소를 하나의 문자열로 합침
        // => Web Publishe 문자열이 span 텍스트로 할당
        spanEl.textContent = currentTxt.join("");
        if(currentTxt.length!==0){
            setTimeout(deleteTxt,Math.floor(Math.random*100));
        }else{
            index=(index+1)%txtArr.length;
            currentTxt = txtArr[index].split("");
            //  write, delete 무한 반복
            writeTxt();
        }
    }
    })();
    
    
    
    const headerE1 = document.querySelector("header");
        window.addEventListener('scroll', function(){
            requestAnimationFrame(scrollCheck);
        });
    function scrollCheck(){
        let browerScrollY = window.scrollY ? window.scrollY : window.pageYOffset;
        if(browerScrollY>0){
        headerE1.classList.add("active");
        }else{
        headerE1.classList.remove("active");
        }   
    }
    
    // 헤더 영역의 메뉴를 클릭하면 메뉴 영역으로 스크롤이 부드럽게 이동하는 효과
    const animationMove = function(selector){
        // selector 매개변수로 이동할 대상 요소 가져오기
        const targetEl = document.querySelector(selector);
        // 현재 웹 브라우저 스크롤 정보(y 값)
        const browerScrollY = window.pageYOffset;
        // 이동할 대상의 위치(y 값)
        const targetScrollY = targetEl.getBoundingClientRect().top + browerScrollY;
        // 스크롤 이동
        window.scrollTo({top: targetScrollY, behavior: 'smooth'});
    };
    
    // 스크롤 이벤트 연결
    const scollMoveEl = document.querySelectorAll("[data-animation-scroll='true']");
        for(let i=0; i<scollMoveEl.length;i++){
            scollMoveEl[i].addEventListener('click', function(e){
                const target = this.dataset.target;
                animationMove(target);
            });
        }