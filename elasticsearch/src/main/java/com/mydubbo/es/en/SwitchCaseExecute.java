package com.mydubbo.es.en;


public class SwitchCaseExecute {

    public static CaseResponse execute(CaseRequest request){
        CaseResponse response;
        switch (request.getType()){
            case "A":
                response= methodA(request);
                break;
            case "B":
                response= methodB(request);
                break;
            //………
            default:
                response= methodC(request);
                break;
        }
        return response;
    }

    public static CaseResponse methodA(CaseRequest request){
        CaseResponse response=new CaseResponse();
        response.setResult("A"+request.getName());
        return response;
    }

    public static CaseResponse methodB(CaseRequest request){
        CaseResponse response=new CaseResponse();
        response.setResult("B"+request.getName());
        return response;
    }

    public static CaseResponse methodC(CaseRequest request){
        CaseResponse response=new CaseResponse();
        response.setResult("C"+request.getName());
        return response;
    }

    public static void main(String[] args) {
        CaseRequest request=new CaseRequest("A","这是名字");
        System.out.println(execute(request));
    }
}

