import k6Login from "./login/k6-login.js";
import {group, sleep} from 'k6';

export default () => {
    group('Endpoint Guardian Login - LoginBusinessServiceController', () => {
        k6Login();
    });
}