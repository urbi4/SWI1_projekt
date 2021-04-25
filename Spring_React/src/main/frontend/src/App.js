import React, {useState} from "react";
import {Button, Card, Col, Container, Form, ListGroup, Row} from "react-bootstrap";
import DateTimePicker from 'react-datetime-picker';

import "react-datepicker/dist/react-datepicker.css";

class App extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            name: "",
            surname: "",
            phone: "",
            email: "",
            street: "",
            houseNumber: "",
            city: "",
            plate: "",
            date: "",
            time: "",
            vehicle: "",
            problems: [],
            data: []
        };
        this.labels = {
            name: "Jméno",
            surname: "Příjmení",
            phone: "Tel. číslo",
            email: "Email",
            street: "Ulice",
            houseNumber: "Číslo domu",
            city: "Město",
            plate: "SPZ",
        }
        this.errors = 0
        this.button = true
    }

    onChange = (e, name) => {
        this.checkValue(e, name)
        this.setState({
            [name]: e
        })
        this.setButton()
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.date !== "" && prevState.date !== this.state.date) {
            this.getTimes(this.state.date)
        }
    }

    setButton = () => {
        let empty = 0;

        for(let item in this.state){
            if (this.state[item].length === 0){
                empty++;
            }
        }

        if (this.errors > 0 || empty > 0) {
            this.button = true
        } else {
            this.button = false
        }

        this.forceUpdate()

    }

    checkValue = (text, name) => {
        switch (name) {
            case "name":
            case "surname":
            case "street":
            case "city": {
                this.checkText(text, name)
                break;
            }
            case "phone": {
                this.checkPhone(text, name)
                break;
            }
            case "email": {
                this.checkEmail(text, name)
                break;
            }
            case "houseNumber": {
                this.checkHouseNumber(text, name)
                break;
            }
            case "plate": {
                this.checkPlate(text, name)
                break;
            }
        }
    }

    checkText = (text, name) => {
        if (!/^[a-záčďěéíňóřšťúůýž]+$/.test(text.toLowerCase())) {
            this.setLabel(text, name, true)
        } else {
            this.setLabel(text, name, false)
        }
    }

    checkPhone = (text, name) => {
        if (!/^\+[0-9]{12}$/.test(text)) {
            this.setLabel(text, name, true)
        } else {
            this.setLabel(text, name, false)
        }
    }

    checkEmail = (text, name) => {
        if (!/^[a-zA-Z.]+@[a-zA-Z]+.[a-zA-Z.]+$/.test(text.toLowerCase())) {
            this.setLabel(text, name, true)
        } else {
            this.setLabel(text, name, false)
        }
    }

    checkHouseNumber = (text, name) => {
        if (!/^[0-9]{1,10}(\/[0-9]{1,10})*$/.test(text)) {
            this.setLabel(text, name, true)
        } else {
            this.setLabel(text, name, false)
        }
    }

    checkPlate = (text, name) => {
        if (!/^[0-9a-z]{5,8}$/.test(text.toLowerCase())) {
            this.setLabel(text, name, true)
        } else {
            this.setLabel(text, name, false)
        }
    }

    setLabel = (text, name, error) => {
        if (error && !this.labels[name].includes("!")) {
            this.labels[name] += " - chybný formát !"
            this.errors++
        }

        if (!error && this.labels[name].includes("!")) {
            let lengthOfLabel = this.labels[name].length
            this.labels[name] = this.labels[name].substr(0, lengthOfLabel - 17)
            this.errors--
        }


    }

    getTimes(date) {
        const axios = require('axios').default;
        axios.get('http://localhost:8080/times', {
            params: {
                value: date
            }
        })
            .then((e) => {
                this.setState({"data": e.data})
            });
    }

    postToBack = () => {
        const axios = require('axios').default;

        axios({
            method: 'post',
            url: 'http://localhost:8080/orders',
            data: {/*
                "description": this.state.description,
                "date": this.state.date.toISOString(),
                "price": this.state.price*/
            }
        }).then((response) => {
            console.log(response);
        }, (error) => {
            console.log(error);
        });
    }

    check = (e,name) => {
        if (this.state.problems.includes(name)){
            this.state.problems.splice(this.state.problems.indexOf(name))
        }else {
            this.state.problems.push(name)
        }
        this.setButton()
    }

    render() {

        const DisplayItems = (data) => {
            const list = data.children.map(item => <option key={item}>{item}</option>)
            return (
                <Form.Group controlId="exampleForm.SelectCustom">
                    <Form.Control as="select" htmlSize={5} custom style={{ width: "80%"}} onChange={(e) => this.onChange(e.target.value, 'time')} value={this.state.time}>
                        {list}
                    </Form.Control>
                </Form.Group>
            )
        }

        return (

            <Container>
                <Card style={{
                    width: 800,
                    border: 0
                }}>
                    <Card.Body>
                        <Card.Title style={{
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center"
                        }}>Registrace nové objednávky</Card.Title>
                        <Form>

                            <Row className="mt-3">
                                <Col>
                                    <Form.Group id="nameId">
                                        <Form.Label>{this.labels.name}</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="např. Karel"
                                            value={this.state.name}
                                            onChange={(e) => this.onChange(e.target.value, 'name')}
                                        />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group id="surnameId">
                                        <Form.Label>{this.labels.surname}</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="např. Novák"
                                            value={this.state.surname}
                                            onChange={(e) => this.onChange(e.target.value, 'surname')}
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>

                            <Row className="mt-3">
                                <Col>
                                    <Form.Group id="phoneId">
                                        <Form.Label>{this.labels.phone}</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="např. +420605312434"
                                            value={this.state.phone}
                                            onChange={(e) => this.onChange(e.target.value, 'phone')}
                                        />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group id="emailId">
                                        <Form.Label>{this.labels.email}</Form.Label>
                                        <Form.Control
                                            type="text"
                                            placeholder="např. karelnovak@seznam.cz"
                                            value={this.state.email}
                                            onChange={(e) => this.onChange(e.target.value, 'email')}
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>

                            <Row>
                                <Col>
                                    <Row className="mt-3">
                                        <Form.Group id="streetId">
                                            <Form.Label>{this.labels.street}</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="např. Dlouhá"
                                                value={this.state.street}
                                                onChange={(e) => this.onChange(e.target.value, 'street')}
                                            />
                                        </Form.Group>
                                    </Row>
                                    <Row className="mt-3">
                                        <Form.Group id="houseNumberId">
                                            <Form.Label>{this.labels.houseNumber}</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="např. 5568"
                                                value={this.state.houseNumber}
                                                onChange={(e) => this.onChange(e.target.value, 'houseNumber')}
                                            />
                                        </Form.Group>
                                    </Row>
                                    <Row className="mt-3">
                                        <Form.Group id="cityId">
                                            <Form.Label>{this.labels.city}</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="např. Praha"
                                                value={this.state.description}
                                                onChange={(e) => this.onChange(e.target.value, 'city')}
                                            />
                                        </Form.Group>
                                    </Row>
                                </Col>

                                <Col>
                                    <Row className="mt-3">
                                        <Form.Group id="plateId">
                                            <Form.Label>{this.labels.plate}</Form.Label>
                                            <Form.Control
                                                type="text"
                                                placeholder="např. 1T23456"
                                                value={this.state.description}
                                                onChange={(e) => this.onChange(e.target.value, 'plate')}
                                            />
                                        </Form.Group>
                                    </Row>
                                    <Row className="mt-3">
                                        <Form.Group id="exampleForm.ControlSelect1">
                                            <Form.Label>Typ vozidla</Form.Label>
                                            <Form.Control as="select" onChange={(e) => this.onChange(e.target.value, 'vehicle')}>
                                                <option>CAR</option>
                                                <option>VAN</option>
                                                <option>TRUCK</option>
                                            </Form.Control>
                                        </Form.Group>

                                    </Row>
                                    <Row className="mt-5">
                                        <Form.Group id="startWorkId">
                                            <DateTimePicker
                                                disableClock
                                                format="dd/MM/yyyy"
                                                locale="cs-CS"
                                                onChange={(e) => {
                                                    this.onChange(e, 'date')
                                                }}
                                                value={this.state.date}
                                            />
                                        </Form.Group>
                                    </Row>
                                </Col>
                            </Row>

                            <Row className="mt-3">
                                <Col>
                                    <Form.Check type={"checkbox"} label={"Pneumatiky"} id={"pneu"} onChange={(e) => {this.check(e, 'pneu')}}/>
                                    <Form.Check type={"checkbox"} label={"Výměna oleje"} id={"oil"} onChange={(e) => {this.check(e, 'oil')}}/>
                                    <Form.Check type={"checkbox"} label={"Baterie"} id={"battery"} onChange={(e) => {this.check(e, 'battery')}}/>
                                    <Form.Check type={"checkbox"} label={"Klimatizace"} id={"ac"} onChange={(e) => {this.check(e, 'ac')}}/>
                                    <Form.Check type={"checkbox"} label={"Stěrače"} id={"wiper"} onChange={(e) => {this.check(e, 'wiper')}}/>
                                    <Form.Check type={"checkbox"} label={"Kompletní servis"} id={"complete"} onChange={(e) => {this.check(e, 'complete')}}/>
                                    <Form.Check type={"checkbox"} label={"Geometrie"} id={"geometry"} onChange={(e) => {this.check(e, 'geometry')}}/>
                                </Col>
                                <Col>
                                    <DisplayItems>{this.state.data}</DisplayItems>
                                </Col>
                            </Row>

                            <Row>
                                <Col style={{display: 'flex', justifyContent: 'center', alignItems: 'center'}}>
                                    <Button variant="primary" disabled={this.button}
                                            onClick={this.postToBack} type="submit">Uložit</Button>
                                </Col>
                            </Row>

                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        );
    }

}

export default App;
