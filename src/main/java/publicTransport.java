public class publicTransport {
    public class Bus{
        int maxPassenger=30;
        int currentPassenger;
        int intake;
        int busNum;
        int oil;
        int currentSpeed;
        String change;
        String status="운행";
        //운행
        public void drive(int oil){
            this.oil-=1;
        }

        //버스 상태 변경
        public void changeStatus(String Bus) {
            if(this.oil<10){
                this.status="차고지행";
                System.out.println("주유가 필요하다");
            }
            else this.status="운행";
        }
        //승객탑승
        public void customerOnboard(int currentPassenger){
            if (currentPassenger<maxPassenger) this.currentPassenger++;
        }
        //속도변경
        public void changeSpeed(int currentSpeed,String change){
            if(change=="증가") this.currentSpeed+=20;
            if(change=="감소") this.currentSpeed-=20;

        }

    }


}
