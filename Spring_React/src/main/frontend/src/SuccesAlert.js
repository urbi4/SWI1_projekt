import React,{ Component } from "react";

export default class SuccesAlert extends Component{
    render() {
        return(
            <div className="alert alert succes" role="alert">
                Recort created successfully.
            </div>
        );
    }
}