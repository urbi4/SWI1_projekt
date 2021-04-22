import React from "react";
import {Button, Card, Col, Container, Form, ListGroup, Row} from "react-bootstrap";
import DateTimePicker from 'react-datetime-picker';


class App extends React.Component {

    constructor(props) {
        super(props);

        this.state = {
            name:"",
            surname:"",
            phone:"",
            email:""
        };
    }

    onChange = (e, name) => {
        this.setState({
            [name]: e
        })
    }


    render() {

        return (
            <Container>
                <Card>
                    <Card.Body>
                        <Card.Title style={{
                            display: "flex",
                            justifyContent: "center",
                            alignItems: "center"
                        }}>Registrace nové objednávky</Card.Title>
                        <Form>

                            <Row className="m-3">
                                <Col>
                                    <Form.Group controlId="nameId">
                                        <Form.Control
                                            type="text"
                                            placeholder="např. Karel"
                                            value={this.state.description}
                                            onChange={(e) => this.onChange(e.target.value, 'name')}
                                        />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group controlId="surnameId">
                                        <Form.Control
                                            type="text"
                                            placeholder="např. Novák"
                                            value={this.state.description}
                                            onChange={(e) => this.onChange(e.target.value, 'surname')}
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>

                            <Row className="m-3">
                                <Col>
                                    <Form.Group controlId="phoneId">
                                        <Form.Control
                                            type="text"
                                            placeholder="např. 605312434"
                                            value={this.state.description}
                                            onChange={(e) => this.onChange(e.target.value, 'phone')}
                                        />
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group controlId="emailId">
                                        <Form.Control
                                            type="text"
                                            placeholder="např. karelnovak@seznam.cz"
                                            value={this.state.description}
                                            onChange={(e) => this.onChange(e.target.value, 'email')}
                                        />
                                    </Form.Group>
                                </Col>
                            </Row>


                            <Col>
                                <Form.Group controlId="startWorkId">
                                    <Form.Label>date</Form.Label>
                                    <DateTimePicker
                                        onChange={(e) => this.onChange(e, 'date')}
                                        value={this.state.date}
                                    />
                                </Form.Group>
                            </Col>

                            <Button variant="primary" onClick={this.createNew} type="submit">Create</Button>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        );
    }
}

export default App;
