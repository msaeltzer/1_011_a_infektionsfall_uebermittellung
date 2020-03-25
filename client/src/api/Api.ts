const METHOD = {
    GET: "GET",
    POST: "POST",
    PUT: "PUT",
    DELETE: "DELETE"
  };
  
class Api {
    private BASE_URL: string; 

    constructor() {
        if (
        location.host.includes("localhost") ||
        location.host.includes("127.0.0.1")
        ) {
        this.BASE_URL = "http://localhost:8080";
        } else {
        this.BASE_URL = "https://api.imis-prototyp.de";
        }
    }

    executeRequest(url: string, method: string, body?: any) {

        // set request options
        const options: any = {
            method: method,
            headers: {
                "Content-Type": "application/json"
            }
        };
        
        if (body) {
            options.body = JSON.stringify(body);
        }

        // return http request
        return fetch(encodeURI(`${this.BASE_URL}/${url}`), options).then(
            // check if error occured
            response => {
                if (response.ok) {
                    return response.json();
                } else {
                    console.error(response);
                    throw new Error(`An error occured: Status ${response.status}`);
                }
            }
        );
    }

    /*
        * -----------IMPLEMENTED IN UI-----------
        */

    postInstitution(institution: any) {
        return this.executeRequest("/institutions", METHOD.POST, institution);
    }

    postPatient(patient: any) {
        return this.executeRequest("/patients", METHOD.POST, patient);
    }

    postLabTest(labtest: any) {
        return this.executeRequest("/labtests", METHOD.POST, labtest);
    }

    /*
        * ---------NOT IMPLEMENTED IN UI---------
        */

    postDoctorCreateAppointment({ doctorId, laboratoryId, patientId }: any) {
        return this.executeRequest("/doctor/create_appointment", 
            METHOD.POST, {
                doctorId,
                laboratoryId,
                patientId
        });
    }

    getTestReports(testId: string) {
        return this.executeRequest(`/test_reports/${testId}`, METHOD.GET);
    }

    postTestReport(id: string, file: any) {
        const data = new FormData();
        data.append("file", file);

        return fetch(`${this.BASE_URL}/test_reports/${encodeURI(id)}`, {
            method: "POST",
            body: data
        }).then(response => {
            return response.json();
        });
    }

    getTestReport(testId: string) {
        return this.executeRequest(`/test_reports/${testId}`, METHOD.GET);
    }

    deleteTestReport(testId:string) {
        return this.executeRequest(`/test_reports/${testId}`, METHOD.DELETE);
    }

    putLabtest({ updatedTestStatus }: any) {
        return this.executeRequest("/labtest", METHOD.PUT, updatedTestStatus);
    }

    getLabtestByPatient(patientId: string) {
        return this.executeRequest(`/labtest/patient/${patientId}`, METHOD.GET);
    }

    getPatients() {
        return this.executeRequest("/patients", METHOD.GET);
    }

    getPatient(id: string) {
        return this.executeRequest(`/patients/${id}`, METHOD.GET);
    }

    getStats(lowerBoundsZip: any, upperBoundsZip: any) {
        return this.executeRequest(
        `/stats?lowerBoundsZip=${lowerBoundsZip}&upperBoundsZips=${upperBoundsZip}`,
        METHOD.GET
        );
    }
}
  
  export default new Api();
  