from sys import argv
from socket import socket, AF_INET, SOCK_STREAM


def main(server_IP, server_port: int):
    # initiate socket with IP version 4 and TCP
    client_socket = socket(AF_INET, SOCK_STREAM)
    # connecting to one of the server's allocated sockets
    client_socket.connect((server_IP, server_port))
    # getting message from user
    msg = input("")
    # repeting the loop until getting "quit"
    while not msg == 'quit':
        # sending message to server
        client_socket.send(bytes(msg, 'utf-8'))
        # reciving the message from server
        data = client_socket.recv(4096)
        # printing it
        print(data.decode('utf-8'), end="")
        # getting another message
        msg = input("")
    # closing socket
    client_socket.close()



if __name__ == "__main__":
    IP_address = argv[1]
    port_address = int(argv[2])
    main(IP_address, port_address)