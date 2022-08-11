let loadDealsBtn = document.getElementById('loadDeals')

loadDealsBtn.addEventListener('click', onLoadDeals);

function onLoadDeals(event) {

        var requestOptions = {
          method: 'GET',
          redirect: 'follow'
        };

        let dealsContainer = document.getElementById('deals-container')
        dealsContainer.innerHTML = ''

        fetch("http://localhost:8080/api/customer", requestOptions)
          .then(response => response.json())
          .then(json => json.forEach(deal => {


            let row = document.createElement('tr')

            let typeCol = document.createElement('th')
            let dateCol = document.createElement('th')
            let mawbCol = document.createElement('th')
            let hawbCol = document.createElement('th')
            let companyCol = document.createElement('th')
            let pcsCol = document.createElement('th')
            let aWtCol = document.createElement('th')
            let cWtCol = document.createElement('th')
            let countryCol = document.createElement('th')
            let airportCol = document.createElement('th')

            typeCol.textContent = deal.type
            dateCol.textContent = deal.date
            mawbCol.textContent = deal.mawb
            hawbCol.textContent = deal.hawb
            companyCol.textContent = deal.company
            pcsCol.textContent = deal.pieces
            aWtCol.textContent = deal.actualWeight
            cWtCol.textContent = deal.chargeableWeight
            countryCol.textContent = deal.country
            airportCol.textContent = deal.airport

            row.appendChild(typeCol)
            row.appendChild(dateCol)
            row.appendChild(mawbCol)
            row.appendChild(hawbCol)
            row.appendChild(companyCol)
            row.appendChild(pcsCol)
            row.appendChild(aWtCol)
            row.appendChild(cWtCol)
            row.appendChild(countryCol)
            row.appendChild(airportCol)

            dealsContainer.appendChild(row);
          }))
          .catch(error => console.log('error', error));


}