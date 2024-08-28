# IPv4 socket scanner for ephemeral ports
import asyncio
import socket

async def main():
    TIMEOUT = 5

    ip = input("Enter the ip that you would like to scan the ports of:\n")

    portRange = input("Please enter the port range you'd like to scan:\n Ex: to scan ports 0 to 1023 type '0,1023'\n").split(',')
    firstPort = int(portRange[0])
    lastPort = int(portRange[1])

    if(firstPort < 0 or lastPort < 0):
        print('Error: Port range cannot be negative.')
        return
    elif(firstPort > 65535 or lastPort > 65535):
        print('Error: Maximum port range is 65535.')
        return

    print('~~~~~~~~~~~~ Address ' + ip + ' ~~~~~~~~~~~~')
    for port in range(firstPort, lastPort+1):
        await checkPort(ip, port)


async def checkPort(ip, port):
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    try:
        s.connect((ip, port))
    except ConnectionRefusedError as e:
        print(f"\t[Port {port}]  Connection refused: ", e)
        return False
    except Exception as e:
        print(f"\t[Port {port}]  An error has occurred: ", e)
        return False
    finally:
        s.close()

    print(f"\t[Port {port}]  OPEN!")
    return True

if __name__ == "__main__":
    asyncio.run(main())
