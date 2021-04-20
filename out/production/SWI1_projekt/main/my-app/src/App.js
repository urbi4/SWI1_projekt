import './App.css';

function App() {
  return (
    <div className="App">

      <div className="d-flex justify-content-center">
        <div className="w-50 p-3">

          <form>

            <div className="row">

              <div className="col-md-4 mb-3">
                <div className="form-group">
                  <label id="nameLabel" className="col-md-4 control-label" htmlFor="name">Jméno</label>
                  <input id="name" name="textinput" type="text" placeholder="např. Pavel"
                         className="form-control input-md" required=""/>
                </div>
              </div>

              <div className="col-md-4 mb-3">
                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="surname">Příjmení</label>
                  <input id="surname" name="textinput" type="text" placeholder="např. Novák"
                         className="form-control input-md" required=""/>
                </div>
              </div>

            </div>

            <div className="row">

              <div className="col-md-4 mb-3">
                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="email">Email</label>
                  <input id="email" name="email" type="text" placeholder="např. pnovak@seznam.cz"
                         className="form-control input-md" required=""/>
                </div>
              </div>

              <div className="col-md-4 mb-3">
                <div className="form-group">
                  <label className="col-md-4 control-label" htmlFor="phone">Telefon</label>
                  <input id="phone" name="textinput" type="text" placeholder="např. +420605605605"
                         className="form-control input-md" required=""/>
                </div>
              </div>

            </div>

            <div className="row">
              <div className="col-md-4 mb-3">

                <div className="row">
                  <div className="col mb-3">
                    <div className="form-group">
                      <label className="col-md-4 control-label" htmlFor="street">Ulice</label>
                      <input id="street" name="street" type="text" placeholder="např. Hlavní"
                             className="form-control input-md" required=""/>
                    </div>
                  </div>
                </div>

                <div className="row">
                  <div className="col mb-3">
                    <div className="form-group">
                      <label className="col-md-4 control-label" htmlFor="number">Číslo</label>
                      <input id="number" name="number" type="text" placeholder="např. 25"
                             className="form-control input-md" required=""/>
                    </div>
                  </div>
                </div>

                <div className="row">
                  <div className="col mb-3">
                    <div className="form-group">
                      <label className="col-md-4 control-label" htmlFor="city">Město</label>
                      <input id="city" name="city" type="text" placeholder="např. Praha"
                             className="form-control input-md" required=""/>
                    </div>
                  </div>
                </div>

              </div>

              <div className="col-md-4 mb-3">

                <div className="row">
                  <div className="col mb-3">
                    <div className="form-group">
                      <label className="col-md-4 control-label" htmlFor="plate">SPZ</label>
                      <input id="plate" name="plate" type="text" placeholder="např. 1T2 3456"
                             className="form-control input-md" required=""/>
                    </div>
                  </div>
                </div>

                <div className="row">
                  <div className="col mb-3">
                    <div className="form-group">
                      <label className="col-md-4 control-label" htmlFor="selectbasic">Typ vozu</label>
                      <select id="selectbasic" name="selectbasic" className="form-control">
                        <option value="1">CAR</option>
                        <option value="2">VAN</option>
                        <option value="3">TRUCK</option>
                      </select>
                    </div>
                  </div>
                </div>

              </div>
            </div>

            <div className="row">
              <div className="col-md-4 mb-3">

                <div className="form-group">
                  <label className="col-md-4 control-label">Problémy</label>
                  <div className="checkbox">
                    <label htmlFor="checkboxes-0">
                      <input type="checkbox" name="checkboxes" id="checkboxes-0" value="1"/>
                        Pneumatiky
                    </label>
                  </div>
                  <div className="checkbox">
                    <label htmlFor="checkboxes-1">
                      <input type="checkbox" name="checkboxes" id="checkboxes-1" value="2"/>
                        Výměna oleje
                    </label>
                  </div>
                  <div className="checkbox">
                    <label htmlFor="checkboxes-2">
                      <input type="checkbox" name="checkboxes" id="checkboxes-2" value="3"/>
                        Baterie
                    </label>
                  </div>
                  <div className="checkbox">
                    <label htmlFor="checkboxes-3">
                      <input type="checkbox" name="checkboxes" id="checkboxes-3" value="4"/>
                        Klimatizace
                    </label>
                  </div>
                  <div className="checkbox">
                    <label htmlFor="checkboxes-4">
                      <input type="checkbox" name="checkboxes" id="checkboxes-4" value="5"/>
                        Stěrače
                    </label>
                  </div>
                  <div className="checkbox">
                    <label htmlFor="checkboxes-5">
                      <input type="checkbox" name="checkboxes" id="checkboxes-5" value="6"/>
                        Kompletní servis
                    </label>
                  </div>
                  <div className="checkbox">
                    <label htmlFor="checkboxes-6">
                      <input type="checkbox" name="checkboxes" id="checkboxes-6" value="7"/>
                        Geometrie
                    </label>
                  </div>
                </div>


              </div>

            </div>


            <div className="row">
              <div className="d-flex justify-content-center">
                <div className="col-md-4 mb-3">

                  <div className="form-group">
                    <label className="col-md-4 control-label" htmlFor="singlebutton"></label>
                    <div className="col-md-4">
                      <button id="sb" type="button" name="sb" className="btn btn-primary">Uložit</button>
                    </div>
                  </div>

                </div>
              </div>
            </div>


          </form>

        </div>
      </div>

    </div>
  );
}

export default App;
