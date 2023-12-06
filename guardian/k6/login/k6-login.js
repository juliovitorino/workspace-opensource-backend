import http from 'k6/http';
import {sleep, check} from 'k6';
import {Trend, Rate, Counter} from 'k6/metrics';
import {heck, fail} from 'k6'

export let postGuardianLoginDuration = new Trend('post_guardian_login_duration');
export let postGuardianLoginFailRate = new Rate('post_guardian_login_fail_rate');
export let postGuardianLoginSucessRate = new Rate('post_guardian_login_success_rate');
export let postGardianLoginRequests = new Rate('post_guardia_login_requests');

export default function () {

    // prepare endpoint url, body and header
    const body = JSON.stringify({'applicationExternalUUID' : 'ce36bef8-7322-4202-9ef2-a4165ac2f9a4','email' : 'admin@gmail.com','codePass' : 'admin'});
    let params = { headers: { 'Content-Type': 'application/json' } }
    let url = 'http://localhost:8080/v1/api/business/login';

    // perform the endpoint call
    let response = http.post(url, body, params);
    //console.log(response.body);

    // add tags to the result panel
    postGuardianLoginDuration.add(response.timings.duration);
    postGardianLoginRequests.add(1);
    postGuardianLoginFailRate.add(response.status == 0 || response.status > 399);
    postGuardianLoginSucessRate.add(response.status < 399);

    check(response, {
        'is status 200' : (r) => r.status === 200,
     });
    sleep(1);
}

