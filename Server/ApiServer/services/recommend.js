import net from 'net'

const recommendPort = process.env.RECOMMEND_PORT || 3001;
const recommendHost = process.env.RECOMMEND_HOST || 'recommend';

//TCP client function
const tcpRequest = (command, user, movie,) => {
    return new Promise((resolve, reject) => {
        const client = net.createConnection({ port: recommendPort, host: recommendHost}, () => {
            client.write(`${command} ${user} ${movie}`)
        })

        client.on('data', data => {
            // close TCP client after data is received
            client.end()

            //debug - print data
            console.log(data.toString())

            // split the data into status msg and data msg
            const lines = data.toString().split('\n').filter(line => line.trim() != '')
            const statusLine = lines[0]
            const statusCode = statusLine.split(' ')[0]

            if (statusCode === '200' && lines.length > 1) {
                const movieLine = lines[1]
                const movies = movieLine.split(' ')
                resolve(movies)
            } else if(statusCode === '201' || statusCode == '204' || statusCode ==='200') {
                resolve()
            } else if (statusCode === '404') {
                reject(new Error('Make sure user has already watched a movie'))
            } else {
                reject(new Error('Failed to process TCP request'))
            }
        })

        client.on('end', () => {
            console.log('Disconnected from recommend service')
        })

        client.on('error', (err) => {
            console.error('Error in recommend service: ', err)
            reject(err)
        })
    })
}


const getRecommendService = async (user, movie) => {
    try {
        const data = await tcpRequest('GET', user, movie)
        return data
    } catch (e) {
        console.error('Error in getRecommendService: ', e)
        throw e
    }
}

const postRecommendService = async (user, movie) => {
    try {
        // use the GET command to identify if the user is already in the system or not.
        await tcpRequest('GET', user, movie)
        //if it does -> send patch request
        await tcpRequest('PATCH', user, movie)
    } catch (e) {
        if (e.message.includes('Failed to process TCP request')) {
            // if the PATCH failed -> user doesnt exist so we try to POST
            await tcpRequest('POST', user, movie)
        } else {
            throw e
        }
    }
}

const deleteRecommendService = async (user, movie) => {
    try {
        const data = await tcpRequest('DELETE', user, movie)
        return data
    } catch (e) {
        console.error('Error in deleteRecommendService: ', e)
        return new Error('recommend fetch failed')
    }
}

export { getRecommendService, postRecommendService, deleteRecommendService }